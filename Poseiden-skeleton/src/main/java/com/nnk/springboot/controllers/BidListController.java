package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
   * @return the template for bid form
   */
  @GetMapping("/bidList/add")
  public String addBidForm(CreateBidListDto createBidListDto) {
    return "bidList/add";
  }

  /**
   * This method create a new bid: validate data and save it to db.
   *
   * @param createBidListDto the bid to save
   * @param result           binding to check if there are errors in parameters
   * @return the list of all bids.
   */
  @PostMapping("/bidList/add")
  public String validate(@Valid CreateBidListDto createBidListDto, BindingResult result) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for bid: "
          + createBidListDto
          + " with error : "
          + result.getFieldErrors());
      return "bidList/add";
    }

    bidListService.createBid(createBidListDto);

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

    model.addAttribute("updateBidListDto", bid);

    return "bidList/update";
  }

  /**
   * This method update a bid with its ID.
   * validate data, update data in DB and return the list of bids
   *
   * @param id               the id of the bid to update
   * @param updateBidListDto the data to update
   * @param result           the fields error in parameters
   * @return the list of all bids.
   */
  @PutMapping("/bidList/update/{id}")
  public String updateBid(@PathVariable("id") int id, @Valid UpdateBidListDto updateBidListDto,
                          BindingResult result, Model model) {

    log.info("updating BidList: " + id);

    if (result.hasErrors()) {

      log.warn("KO - Error in validation for bid: "
          + updateBidListDto
          + " with error : "
          + result.getFieldErrors());
      model.addAttribute("updateBidListDto", updateBidListDto);
      return "/bidList/update";

    }


    bidListService.updateBid(id, updateBidListDto);

    return "redirect:/bidList/list";
  }

  /**
   * This method delete the bid with the given id.
   *
   * @param id the id of the bid to delete
   * @return the list of all bids.
   */
  @DeleteMapping("/bidList/delete/{id}")
  public String deleteBid(@PathVariable("id") int id, Model model) {

    bidListService.deleteBid(id);

    return "redirect:/bidList/list";
  }
}
