package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.ConfirmationCode;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import kg.melakuera.springwebcontent.repository.ConfirmationCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
@Log
public class ConfirmationCodeService {

    private ConfirmationCodeRepository confirmationCodeRepository;
    private AppUserRepository appUserRepository;

    public boolean confirm(String code) {
        ConfirmationCode confirmationCode = confirmationCodeRepository.findByCode(code)
                .orElse(null);
        assert confirmationCode != null;
        if (confirmationCode.getExpiredAt().isBefore(LocalDateTime.now())) {

            log.info("Истекло время подтверждение пользователя!");
            return false;
        }
        String email = confirmationCode.getAppUser().getEmail();
        appUserRepository.updateEnabled(email);

        log.info(String.format("Пользователь c данной эл. почтой %s подтвержден!", email));
        return true;
    }
}
