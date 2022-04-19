package ru.sbrf.sbercrm.saas.service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframewok.web.multipart.MultipartFile;
import ru.sbrf.sbercrm.saas.dto.CommonRqDto;
import ru.sbrf.sbercrm.saas.dto.CommonRsDto;
import ru.sbrf.sbercrm.saas.model.Attachemnt;
import ru.sbrf.sbercrm.sass.model.FileEncoded;

public interface AttachmentService {
    CommonRsDto getAll(CommonRqDto commonRqDto);

    Attachment upload(MultipartFile multipartFile, UUID objectId) throws IOException;

    void download(UUID id, HttpServletResponse response);
    
    void delete(UUID id);

    fileEncoded getFileEncoded(UUID id);

    void getBinary(UUID id, HttpServletResponse response);

    InputStreamResource getByLastCreatedAndObjectIdAndContainsExtensionAsStrem(UUID objectId, Set<String> extensions);
}
