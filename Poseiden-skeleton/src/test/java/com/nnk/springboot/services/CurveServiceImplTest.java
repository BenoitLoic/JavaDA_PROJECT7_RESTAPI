package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurveServiceImplTest {


  @Mock
  CurvePointRepository curvePointRepositoryMock;
  @InjectMocks
  CurveServiceImpl curveService;

  @Test
  void findAllCurvePoint() {

    // GIVEN
    List<CurvePoint> curvePoints = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      CurvePoint temp = new CurvePoint();
      temp.setId(i);
      curvePoints.add(temp);
    }
    // WHEN
    when(curvePointRepositoryMock.findAll()).thenReturn(curvePoints);
    // THEN
    assertEquals(3, curveService.getAllCurvePoint().size());

  }

  @Test
  void findAllCurvePointEmptyList() {

    // GIVEN

    // WHEN
    when(curvePointRepositoryMock.findAll()).thenReturn(new ArrayList<>());
    // THEN
    assertEquals(0, curveService.getAllCurvePoint().size());

  }

  @Test
  void createCurvePoint() {
    // GIVEN
    CreateCurvePointDto createCurvePointDto = new CreateCurvePointDto();
    createCurvePointDto.setCurveId(1);
    createCurvePointDto.setAsOfDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    CurvePoint expected = new CurvePoint();
    expected.setCurveId(1);
    expected.setAsOfDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    // WHEN
    curveService.createCurvePoint(createCurvePointDto);
    // THEN
    verify(curvePointRepositoryMock, times(1)).save(expected);

  }


  @Test
  void getCurvePointWithIdValid() {
    // GIVEN
    CurvePoint curvePoint = new CurvePoint();
    curvePoint.setId(5);
    curvePoint.setCurveId(1);
    curvePoint.setAsOfDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    // WHEN
    when(curvePointRepositoryMock.findById(5)).thenReturn(Optional.of(curvePoint));
    UpdateCurvePointDto actual = curveService.getPointWithId(5);
    // THEN
    assertEquals(5, actual.getId());
    assertEquals(1, actual.getCurveId());

  }

  @Test
  void getCurvePointWithIdWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(curvePointRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    assertThrows(DataNotFoundException.class, () -> curveService.getPointWithId(1));

  }

  @Test
  void updateCurvePointValid() {
    // GIVEN

    CurvePoint curvePoint = new CurvePoint();
    curvePoint.setId(9);
    curvePoint.setTerm(5.96);

    LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    UpdateCurvePointDto curvePointDto = new UpdateCurvePointDto();
    curvePointDto.setTerm(6.2);
    curvePointDto.setAsOfDate(time);

    CurvePoint expected = new CurvePoint();
    expected.setId(9);
    expected.setTerm(6.2);
    expected.setAsOfDate(time);


    // WHEN
    Mockito.when(curvePointRepositoryMock.findById(anyInt())).thenReturn(Optional.of(curvePoint));
    curveService.updateCurvePoint(9, curvePointDto);


    // THEN

    verify(curvePointRepositoryMock).save(expected);

  }

  @Test
  void updateCurvePointWithNoChange() {
    // GIVEN
    CurvePoint curvePoint = new CurvePoint();
    curvePoint.setCurveId(9);
    curvePoint.setId(2);

    UpdateCurvePointDto updateCurvePointDto = new UpdateCurvePointDto();
    // WHEN
    when(curvePointRepositoryMock.findById(anyInt())).thenReturn(Optional.of(curvePoint));
    curveService.updateCurvePoint(2, updateCurvePointDto);
    // THEN
    verify(curvePointRepositoryMock, times(1)).save(curvePoint);

  }

  @Test
  void updateCurvePointWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(curvePointRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> curveService.updateCurvePoint(2, any()));
  }

  @Test
  void deleteCurvePointValid() {
    // GIVEN

    // WHEN
    when(curvePointRepositoryMock.findById(2)).thenReturn(Optional.of(new CurvePoint()));
    curveService.deleteCurvePoint(2);
    // THEN
    verify(curvePointRepositoryMock, times(1)).deleteById(2);

  }

  @Test
  void deleteCurvePointWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(curvePointRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> curveService.deleteCurvePoint(1));

  }

}