package dev.jaimerey.accounts.service.client;

import dev.jaimerey.accounts.dto.CardsDto;
import dev.jaimerey.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {

        return null;
    }
}
