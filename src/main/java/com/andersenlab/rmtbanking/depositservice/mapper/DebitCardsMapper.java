package com.andersenlab.rmtbanking.depositservice.mapper;

import com.andersenlab.rmtbanking.depositservice.dto.DebitCardsDto;
import com.andersenlab.rmtbanking.depositservice.dto.DebitCardsInfoDto;
import com.andersenlab.rmtbanking.depositservice.entity.Account;
import com.andersenlab.rmtbanking.depositservice.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DebitCardsMapper {

    @Mapping(source = "card.account.accountNumber", target = "accountNumber")
    DebitCardsDto toDto(Card card);

    Card toEntity(DebitCardsDto debitCardsDto);

    List<DebitCardsDto> debitCardsToDebitCardsDto(List<Card> allByAccountId);

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    @Mapping(source = "account.salaryProject", target = "salaryProject")
    @Mapping(source = "account.currentBalance", target = "currentBalance")
    @Mapping(source = "account.currencyCode", target = "currencyCode")
    DebitCardsInfoDto debitCardsInfoToDto(Card card);

    @Named("getDefaultCard")
    default String getDefaultCard(Account account) {
        return account.getCards()
                .stream()
                .filter(Card::isDefaults)
                .findFirst()
                .map(Card::getId)
                .map(UUID::toString)
                .orElse("");
    }
}