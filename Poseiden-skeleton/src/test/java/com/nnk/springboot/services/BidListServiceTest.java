package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

  @Mock
  BidListRepository bidListRepositoryMock;
  @InjectMocks
  BidListServiceImpl bidListService;

  @Test
  public void findAllBids() {

    // GIVEN
    List<BidList> bids = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      BidList temp = new BidList();
      temp.setBidListId(i);
      temp.setAccount("account" + i);
      bids.add(temp);
    }
    // WHEN
    Mockito.when(bidListRepositoryMock.findAll()).thenReturn(bids);

    // THEN
    Assertions.assertThat(bidListService.findAllBids().size()).isEqualTo(3);

  }

  @Test
  public void createBid() {

    // GIVEN
    CreateBidListDto temp = new CreateBidListDto("account", "type", 0.1);
    BidList expected = new BidList();
    expected.setAccount("account");
    expected.setType("type");
    expected.setBidQuantity(0.1);

    // WHEN
    bidListService.createBid(temp);
    // THEN
    verify(bidListRepositoryMock, times(1)).save(expected);

  }

  @Test
  public void getBidWithId() {

    // GIVEN
    BidList bidList = new BidList();
    bidList.setBidListId(2);
    bidList.setAccount("account");
    bidList.setType("type");

    // WHEN
    when(bidListRepositoryMock.findById(anyInt())).thenReturn(Optional.of(bidList));
    UpdateBidListDto actual = bidListService.getBidWithId(2);
    // THEN
    Assertions.assertThat(actual.getBidListId()).isEqualTo(2);
    Assertions.assertThat(actual.getType()).isEqualTo("type");

  }

  @Test
  public void getBidWithIdWhenDataNotFound_ShouldThrowDataNotFoundException() {

    // GIVEN

    // WHEN
    when(bidListRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    org.junit.jupiter.api.Assertions.assertThrows(DataNotFoundException.class,
        () -> bidListService.getBidWithId(2));

  }

  @Test
  public void updateBidWithNoChange() {

    // GIVEN
    BidList bidList = new BidList();
    bidList.setBidListId(1);
    bidList.setAccount("account");


    UpdateBidListDto updateBidListDto = new UpdateBidListDto();

    // WHEN
    when(bidListRepositoryMock.findById(1)).thenReturn(Optional.of(bidList));
    bidListService.updateBid(1, updateBidListDto);
    // THEN
    verify(bidListRepositoryMock, times(1)).save(bidList);
  }

  @Test
  public void updateBidWithFullChange() {

    // GIVEN
    BidList bidList = new BidList();
    bidList.setBidListId(1);
    bidList.setAccount("account");

    UpdateBidListDto updateBidListDto = new UpdateBidListDto();
    updateBidListDto.setBidQuantity(8.4);
    updateBidListDto.setAccount("changed");
    updateBidListDto.setType("type");

    BidList expected = new BidList();
    expected.setBidListId(1);
    expected.setType("type");
    expected.setAccount("changed");
    expected.setBidQuantity(8.4);

    // WHEN
    when(bidListRepositoryMock.findById(1)).thenReturn(Optional.of(bidList));
    bidListService.updateBid(1, updateBidListDto);
    // THEN
    verify(bidListRepositoryMock, times(1)).save(expected);
  }

  @Test
  public void updateBidWhenDataNotFound_ShouldThrowDataNotFoundException() {

    // GIVEN

    // WHEN
    when(bidListRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    org.junit.jupiter.api.Assertions.assertThrows(DataNotFoundException.class,
        () -> bidListService.updateBid(2, Mockito.any()));
  }

  @Test
  public void deleteBid() {

    // GIVEN

    // WHEN
    when(bidListRepositoryMock.findById(anyInt())).thenReturn(Optional.of(new BidList()));
    bidListService.deleteBid(9);
    // THEN
    verify(bidListRepositoryMock, times(1)).deleteById(9);
  }

  @Test
  public void deleteBidWhenDataNotFound_ShouldThrowDataNotFoundException() {

    // GIVEN

    // WHEN
    when(bidListRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    org.junit.jupiter.api.Assertions.assertThrows(DataNotFoundException.class,
        () -> bidListService.deleteBid(1));

  }
}
