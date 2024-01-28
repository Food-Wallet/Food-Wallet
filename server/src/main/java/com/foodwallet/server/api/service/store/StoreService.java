package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyInfoServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyImageResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyInfoResponse;
import com.foodwallet.server.api.service.store.response.StoreOpenResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.operation.Operation;
import com.foodwallet.server.domain.operation.repository.OperationRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.foodwallet.server.api.service.store.StoreValidator.*;
import static com.foodwallet.server.common.message.ExceptionMessage.*;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final OperationRepository operationRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public StoreCreateResponse createStore(String email, StoreCreateServiceRequest request) {
        Member member = memberRepository.findByEmail(email);

        if (!member.isBusinessMember()) {
            throw new AuthenticationException(IS_NOT_BUSINESS_MEMBER);
        }

        if (!member.isExistAccount()) {
            throw new IllegalArgumentException(NO_ACCOUNT_INFORMATION);
        }

        StoreType type = StoreType.of(request.getType());
        String validatedName = nameValidation(request.getName());
        String validatedDescription = descriptionValidation(request.getDescription());

        UploadFile uploadFile = toUploadFile(request.getImage());

        Store store = Store.createStore(type, validatedName, validatedDescription, uploadFile, member);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.of(savedStore);
    }

    public StoreModifyInfoResponse modifyStoreInfo(String email, Long storeId, StoreModifyInfoServiceRequest request) {
        Store store = getMyStore(email, storeId);

        if (store.isOpen()) {
            throw new IllegalArgumentException("영업중에는 매장 정보를 수정할 수 없습니다.");
        }

        StoreType type = StoreType.of(request.getType());
        String validatedName = nameValidation(request.getName());
        String validatedDescription = descriptionValidation(request.getDescription());

        store.modifyInfo(type, validatedName, validatedDescription);

        return StoreModifyInfoResponse.of(store);
    }

    public StoreModifyImageResponse modifyStoreImage(String email, Long storeId, MultipartFile image) {
        Store store = getMyStore(email, storeId);

        if (store.isOpen()) {
            throw new IllegalArgumentException("영업중에는 매장 정보를 수정할 수 없습니다.");
        }

        UploadFile uploadFile = toUploadFile(image);

        store.modifyImage(uploadFile);

        return null;
    }

    public StoreOpenResponse openStore(String email, Long storeId, StoreOpenServiceRequest request) {
        Store store = getMyStore(email, storeId);

        if (store.isOpen()) {
            throw new IllegalArgumentException("이미 매장을 운영하고 있습니다.");
        }

        if (request.getStartTime() == request.getFinishTime()) {
            throw new IllegalArgumentException("운영 시작 시간과 종료 시간을 다르게 설정해주세요.");
        }

        String validatedAddress = addressValidation(request.getAddress());
        String time = generateTime(request.getStartTime(), request.getFinishTime());
        double validatedLatitude = latitudeValidation(request.getLatitude());
        double validatedLongitude = longitudeValidation(request.getLongitude());

        Operation operation = Operation.create(validatedAddress, time, validatedLatitude, validatedLongitude, store);
        Operation savedOperation = operationRepository.save(operation);
        store.open();

        return StoreOpenResponse.of(store, savedOperation);
    }

    /**
     * S3 서버에 파일을 업로드하고 이미지 URL 정보를 반환한다.
     *
     * @param file S3 서버에 업로드할 파일
     * @return 업로드된 이미지 URL 정보
     * @throws RuntimeException S3 서버에 파일 업로드를 실패한 경우
     */
    private UploadFile toUploadFile(MultipartFile file) {
        try {
            return fileStore.storeFile(file);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드를 실패했습니다.", e);
        }
    }

    private Store getMyStore(String email, Long storeId) {
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        if (!store.isMine(member)) {
            throw new AuthenticationException(NOT_AUTHORIZED);
        }

        return store;
    }

    private String generateTime(LocalTime startTime, LocalTime finishTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm");
        String startTimeStr = startTime.format(timeFormatter);
        if (finishTime.isBefore(startTime)) {
            String finishTimeStr = finishTime.format(DateTimeFormatter.ofPattern("익일 h:mm"));
            return String.format("%s ~ %s", startTimeStr, finishTimeStr);
        }

        String finishTimeStr = finishTime.format(timeFormatter);
        return String.format("%s ~ %s", startTimeStr, finishTimeStr);
    }
}
