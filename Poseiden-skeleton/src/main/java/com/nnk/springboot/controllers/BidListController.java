package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.IllegalArgumentException;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * BidList controller.
 * Contains CRUD method for bidList feature.
 */
@Controller
public class BidListController {

  private final Logger log = LogManager.getLogger(getClass().getName());

  private final BidListService bidListService;

  @Autowired
  public BidListController(BidListService bidListService) {
    this.bidListService = bidListService;
  }

  /**
   * This method return all bids saved in DB.
   *
   * @param model the model to inject data for the view
   * @return the list of all bids present in db.
   */
  @GetMapping("/bidList/list")
  public String home(Model model) {

    Collection<GetBidListDto> bids = bidListService.findAllBids();
    model.addAttribute("bidList", bids);
    return "bidList/list";
  }

  /**
   * This method return the form to add a new bid.
   *
   * @param bid ?
   * @return the template for bid form
   */
  @GetMapping("/bidList/add")
  public String addBidForm(BidList bid) {
    return "bidList/add";
  }

  /**
   * This method create a new bid: validate data and save it to db.
   *
   * @param bid    the bid to save
   * @param result binding to check if there are errors in parameters
   * @param model  the model
   * @return the list of all bids.
   */
  @PostMapping("/bidList/validate")
  @ResponseStatus(HttpStatus.CREATED)
  public String validate(@Valid @ModelAttribute CreateBidListDto bid, BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for bid: "
          + bid
          + " with error : "
          + result.getFieldErrors());
      throw new IllegalArgumentException("Error - invalid field." );
    }

    bidListService.createBid(bid);

    return "redirect:/bidList/list";

  }

  /**
   * This method get BidList by Id and return the form to update the given bid.
   *
   * @param id    the id of the bid to update
   * @param model the model to add bid
   * @return the template for bid form update.
   */
  @GetMapping("/bidList/update/{id}")
  public String showUpdateForm(@Valid @PathVariable("id") int id, Model model) {

    log.info("Getting bid with id: " + id);

    UpdateBidListDto bid = bidListService.getBidWithId(id);

    model.addAttribute("bidList", bid);

    return "bidList/update";
  }

  /**
   * This method update a bid with its ID.
   * validate data, update data in DB and return the list of bids
   *
   * @param id     the id of the bid to update
   * @param bid    the data to update
   * @param result the fields error in parameters
   * @param model  the model
   * @return the list of all bids.
   */
  @PutMapping("/bidList/update/{id}")
  public String updateBid(@PathVariable("id") int id, @Valid UpdateBidListDto bid,
                          BindingResult result) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for bid: "
          + bid
          + " with error : "
          + result.getFieldErrors());
      throw new IllegalArgumentException("KO - Error in validation");

    }


    bidListService.updateBid(id, bid);

    return "redirect:/bidList/list";
  }

  /**
   * This method delete the bid with the given id.
   *
   * @param id    the id of the bid to delete
   * @return the list of all bids.
   */
  @DeleteMapping("/bidList/delete/{id}")
  public String deleteBid(@PathVariable("id") int id) {

    bidListService.deleteBid(id);

    return "redirect:/bidList/list";
  }
}
