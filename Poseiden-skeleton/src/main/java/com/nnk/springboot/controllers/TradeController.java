package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.GetTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Contains CRUD method for Trade feature.
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  TradeService tradeService;

  /**
   * Get all Trade.
   *
   * @param model the model to inject data for the view.
   * @return the list of all trades.
   */
  @RequestMapping("/list")
  public String home(Model model) {

    Collection<GetTradeDto> trades = tradeService.findAllTrades();
    model.addAttribute("trades", trades);

    return "trade/list";
  }

  /**
   * Return the form to create a new trade.
   *
   * @return the html form
   */
  @GetMapping("/add")
  public String addForm(CreateTradeDto createTradeDto) {
    return "trade/add";
  }

  /**
   * Create a new trade : validate data and save it to db.
   *
   * @param createTradeDto the trade to save
   * @param result         binding to check if there are errors in parameters
   * @return the list of all trades.
   */
  @PostMapping("/add")
  public String validate(@Valid CreateTradeDto createTradeDto, BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for trade: "
          + createTradeDto
          + " with error : "
          + result.getFieldErrors());
      return "trade/add";
    }

    tradeService.createTrade(createTradeDto);

    return "redirect:/trade/list";
  }

  /**
   * Get Trade by Id, add to model then show to the form to update.
   *
   * @param id    the id of the trade to update
   * @param model the model to add data
   * @return the html form for trade update
   */
  @GetMapping("/update/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {

    log.info("Getting rule with id: " + id);

    UpdateTradeDto trade = tradeService.getTradeWithId(id);

    model.addAttribute("updateTradeDto", trade);

    return "trade/update";
  }

  /**
   * Update a trade with its id.
   * validate data, update data in DB and return all trades.
   *
   * @param id             the id of the trade to update
   * @param updateTradeDto the data to update
   * @param result         the field error in parameters
   * @param model          the model
   * @return the list of all trade
   */
  @PutMapping("/update/{id}")
  public String updateTrade(@PathVariable("id") int id, @Valid UpdateTradeDto updateTradeDto,
                            BindingResult result, Model model) {

    log.info("Updating Trade: " + id);

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for trade: "
          + updateTradeDto
          + " with error : "
          + result.getFieldErrors());
      model.addAttribute("updateTradeDto", updateTradeDto);
      return "/trade/update";
    }

    tradeService.updateTrade(id, updateTradeDto);

    return "redirect:/trade/list";
  }

  /**
   * Delete the trade with the given id.
   *
   * @param id    the id of the trade to delete
   * @param model th model
   * @return the list of all trades
   */
  @DeleteMapping("/delete/{id}")
  public String deleteTrade(@PathVariable("id") int id, Model model) {
    tradeService.deleteTrade(id);
    return "redirect:/trade/list";
  }
}
