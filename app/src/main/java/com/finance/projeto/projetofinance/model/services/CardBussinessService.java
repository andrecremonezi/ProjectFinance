package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.persistence.CardRepository;
import java.util.List;

public class CardBussinessService {

    private CardBussinessService() {
        super();
    }

    public static List<Card> findAll() {
        return CardRepository.getAll();
    }

    public static void save(Card card) {
        CardRepository.save(card);
    }

    public static void delete(Card card){
        CardRepository.delete(card.getId());
    }
}
