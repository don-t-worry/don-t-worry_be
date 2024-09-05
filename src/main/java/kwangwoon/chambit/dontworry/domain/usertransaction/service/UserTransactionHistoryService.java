package kwangwoon.chambit.dontworry.domain.usertransaction.service;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import kwangwoon.chambit.dontworry.domain.user.repository.UserRepository;
import kwangwoon.chambit.dontworry.domain.usertransaction.domain.UserTransactionHistory;
import kwangwoon.chambit.dontworry.domain.usertransaction.dto.request.UserTransactionInsertDto;
import kwangwoon.chambit.dontworry.domain.usertransaction.repository.UserTransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTransactionHistoryService {
    private final UserTransactionHistoryRepository userTransactionRepository;
    private final UserRepository userRepository;

    public UserTransactionHistory occurTransaction(UserTransactionInsertDto userTransactionInsertDto, UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        UserTransactionHistory entity = userTransactionInsertDto.toEntity(user);

        return userTransactionRepository.save(entity);
    }
}
