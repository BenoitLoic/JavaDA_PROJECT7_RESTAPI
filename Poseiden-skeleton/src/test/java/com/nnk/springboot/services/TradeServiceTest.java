package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

  @Mock
  TradeRepository tradeRepositoryMock;
  @InjectMocks
  TradeServiceImpl tradeService;

  @Test
  void findAllTrades() {

    // GIVEN
    List<Trade> trades = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Trade temp = new Trade();
      temp.setTradeId(i);
      temp.setAccount("account" + i);
      trades.add(temp);
    }
    // WHEN
    when(tradeRepositoryMock.findAll()).thenReturn(trades);
    // THEN
    assertEquals(3, tradeService.findAllTrades().size());

  }

  @Test
  void findAllTradesEmptyList() {

    // GIVEN

    // WHEN
    when(tradeRepositoryMock.findAll()).thenReturn(new ArrayList<>());
    // THEN
    assertEquals(0, tradeService.findAllTrades().size());

  }
  @Test
  void createTrade() {
    // GIVEN
    CreateTradeDto createTradeDto = new CreateTradeDto();
    createTradeDto.setAccount("account");
    createTradeDto.setType("type");
    Trade expected = new Trade();
    expected.setAccount("account");
    expected.setType("type");
    // WHEN
    tradeService.createTrade(createTradeDto);
    // THEN
    verify(tradeRepositoryMock, times(1)).save(expected);

  }


  @Test
  void getTradeWithIdValid() {
    // GIVEN
    Trade trade = new Trade();
    trade.setTradeId(5);
    trade.setAccount("account");
    trade.setType("type");
    // WHEN
    when(tradeRepositoryMock.findById(5)).thenReturn(Optional.of(trade));
    UpdateTradeDto actual = tradeService.getTradeWithId(5);
    // THEN
    assertEquals(5, actual.getTradeId());
    assertEquals("type", actual.getType());

  }

  @Test
  void getTradeWithIdWhenDataNotFind_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(tradeRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    assertThrows(DataNotFoundException.class, () -> tradeService.getTradeWithId(1));

  }

  @Test
  void updateTradeValid() {
    // GIVEN

    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setTradeId(1);

    LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    UpdateTradeDto tradeDto = new UpdateTradeDto();
    tradeDto.setType("typeTest");
    tradeDto.setTradeDate(time);

    Trade expected = new Trade();
    expected.setAccount("account");
    expected.setTradeId(1);
    expected.setType("typeTest");
    expected.setTradeDate(time);

    // WHEN
    Mockito.when(tradeRepositoryMock.findById(anyInt())).thenReturn(Optional.of(trade));
    tradeService.updateTrade(1, tradeDto);

    // THEN

    verify(tradeRepositoryMock).save(expected);



  }

  @Test
  void updateTradeWithNoChange() {
    // GIVEN

    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setTradeId(1);

    UpdateTradeDto tradeDto = new UpdateTradeDto();
    // WHEN
    when(tradeRepositoryMock.findById(anyInt())).thenReturn(Optional.of(trade));
    tradeService.updateTrade(1, tradeDto);
    // THEN
    verify(tradeRepositoryMock, times(1)).save(trade);

  }

  @Test
  void updateTradeWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(tradeRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> tradeService.updateTrade(2, any()));
  }

  @Test
  void deleteTradeValid() {
    // GIVEN

    // WHEN
    when(tradeRepositoryMock.findById(2)).thenReturn(Optional.of(new Trade()));
    tradeService.deleteTrade(2);
    // THEN
    verify(tradeRepositoryMock, times(1)).deleteById(2);

  }

  @Test
  void deleteTradeWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(tradeRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> tradeService.deleteTrade(1));

  }
}