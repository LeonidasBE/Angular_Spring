package com.leonidas.HelpDesk.api.controller;

import com.leonidas.HelpDesk.api.dto.Summary;
import com.leonidas.HelpDesk.api.entity.ChangeStatus;
import com.leonidas.HelpDesk.api.entity.Ticket;
import com.leonidas.HelpDesk.api.entity.User;
import com.leonidas.HelpDesk.api.enums.ProfileEnum;
import com.leonidas.HelpDesk.api.enums.StatusEnum;
import com.leonidas.HelpDesk.api.response.Response;
import com.leonidas.HelpDesk.api.security.jwt.JwtTokenUtil;
import com.leonidas.HelpDesk.api.service.TicketService;
import com.leonidas.HelpDesk.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<Ticket>> create(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {
        Response<Ticket> ticketResponse = new Response<Ticket>();
        try {
            validateCreateTicket(ticket, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> ticketResponse.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(ticketResponse);
            }
            ticket.setStatus(StatusEnum.getStatus("New"));
            ticket.setUser(userFromRequest(request));
            ticket.setDate(new Date());
            ticket.setNumber(generateNumber());
            Ticket ticketPersisted = ticketService.createOrUpdate(ticket);
            ticketResponse.setData(ticketPersisted);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }

        return ResponseEntity.ok(ticketResponse);
    }

    public void validateCreateTicket(Ticket ticket, BindingResult result) {
        if(ticket.getTitle() == null)
            result.addError(new ObjectError("Ticket", "Title not informed"));

        if(ticket.getDescription() == null)
            result.addError(new ObjectError("Ticket", "Description not informed"));
    }

    public User userFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.findByEmail(email);
        return user;
    }

    public Integer generateNumber() {
        Random random = new Random();
        return random.nextInt(9999);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {
        Response<Ticket> ticketResponse = new Response<Ticket>();
        try {
            validateUpdateTicket(ticket, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> ticketResponse.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(ticketResponse);
            }
            Ticket ticketCurrent = ticketService.findById(ticket.getId());
            ticket.setStatus(ticketCurrent.getStatus());
            ticket.setUser(ticketCurrent.getUser());
            ticket.setDate(ticketCurrent.getDate());
            ticket.setNumber(ticketCurrent.getNumber());
            if(ticketCurrent.getAssignedUser() != null) {
                ticket.setAssignedUser(ticketCurrent.getAssignedUser());
            }
            Ticket ticketPersisted = ticketService.createOrUpdate(ticket);
            ticketResponse.setData(ticketPersisted);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
    public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") String id) {
        Response<Ticket> ticketResponse = new Response<Ticket>();
        try {
            Ticket ticket = ticketService.findById(id);
            if(ticket == null) {
                ticketResponse.getErrors().add("Ticket not found");
                return ResponseEntity.badRequest().body(ticketResponse);
            }
            List<ChangeStatus> changes = new ArrayList<>();
            Iterable<ChangeStatus> changesIterable = ticketService.listChangeStatus(ticket.getId());
            for(Iterator<ChangeStatus> iterator = changesIterable.iterator(); iterator.hasNext();) {
                ChangeStatus changeStatus = iterator.next();
                changeStatus.setTicket(null);
                changes.add(changeStatus);
            }
            ticket.setChanges(changes);
            ticketResponse.setData(ticket);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }
        return ResponseEntity.ok(ticketResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable String id) {
        Response<String> response = new Response<>();
        Ticket ticket = ticketService.findById(id);
        if(ticket == null) {
            response.getErrors().add("Ticket not found");
            return ResponseEntity.badRequest().body(response);
        }
        ticketService.delete(id);
        return ResponseEntity.ok(new Response<String>());
    }

    @GetMapping("{page}/{count}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
    public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable("page") int page, @PathVariable("count") int count) {
        Response<Page<Ticket>> ticketResponse = new Response<Page<Ticket>>();
        try {
            Page<Ticket> allTickets = null;
            User user = userFromRequest(request);
            if(user.getProfile().equals(ProfileEnum.ROLE_TECHNICIAN)) {
                allTickets = ticketService.listTicket(page, count);
            } else if(user.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
                allTickets = ticketService.findByCurrentUser( page, count, user.getId());
            }
            ticketResponse.setData(allTickets);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }

        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("{page}/{count}/{number}/{title}/{status}/{priority}/{assigned}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
    public ResponseEntity<Response<Page<Ticket>>> findByParameters(HttpServletRequest request,
                                                                    @PathVariable("page") int page,
                                                                    @PathVariable("count") int count,
                                                                    @PathVariable("number") Integer number,
                                                                    @PathVariable("title") String title,
                                                                    @PathVariable("status") String status,
                                                                    @PathVariable("priority") String priority,
                                                                    @PathVariable("assigned") boolean assigned
                                                                    ) {

        title = title.equals("uniformed") ? "" : title;
        status = status.equals("uniformed") ? "" : status;
        priority = priority.equals("uniformed") ? "" : priority;

        Response<Page<Ticket>> ticketResponse = new Response<Page<Ticket>>();
        try {
            Page<Ticket> allTickets = null;
            if(number > 0) {
                allTickets = ticketService.findByNumber(page, count, number);
            } else {
                User userRequest = userFromRequest(request);
                if(userRequest.getProfile().equals(ProfileEnum.ROLE_TECHNICIAN)){
                    if(assigned) {
                        allTickets = ticketService.findByParametersAndAssignedUser(page, count, title, status, priority, userRequest.getId());
                    } else {
                        allTickets = ticketService.findByParameters(page, count, title, status, priority);
                    }
                } else if(userRequest.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
                    allTickets = ticketService.findByParametersAndCurrentUser(page, count, title, status, priority, userRequest.getId());
                }
            }
            ticketResponse.setData(allTickets);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }
        return ResponseEntity.ok(ticketResponse);
    }

    @PutMapping("{id}/{status}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
    public ResponseEntity<Response<Ticket>> changeStatus( HttpServletRequest request,
                                                          @PathVariable("id") String id,
                                                          @PathVariable("status") String status,
                                                          @RequestBody Ticket ticket,
                                                          BindingResult bindingResult
    ) {
        Response<Ticket> ticketResponse = new Response<Ticket>();
        validateChangeStatus(id, status, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> ticketResponse.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(ticketResponse);
        }
        try {
            Ticket ticketCurrent = ticketService.findById(id);
            if(ticketCurrent == null) {
                ticketResponse.getErrors().add("Ticket not found");
                return ResponseEntity.badRequest().body(ticketResponse);
            }
            ticketCurrent.setStatus(StatusEnum.getStatus(status));
            if(status.equals("Assigned")) {
                ticketCurrent.setAssignedUser(userFromRequest(request));
            }
            Ticket ticketPersisted = ticketService.createOrUpdate(ticketCurrent);
            ChangeStatus changeStatus = new ChangeStatus();
            changeStatus.setUserChange(userFromRequest(request));
            changeStatus.setDateChangeStatus(new Date());
            changeStatus.setTicket(ticketCurrent);
            changeStatus.setStatus(StatusEnum.getStatus(status));
            ticketService.createChangeStatus(changeStatus);
            ticketResponse.setData(ticketPersisted);
        } catch (Exception e) {
            ticketResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(ticketResponse);
        }
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("summary")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'TECHNICIAN')")
    public ResponseEntity<Response<Summary>> findSummary() {
        Response<Summary> summaryResponse = new Response<Summary>();
        try {
            Summary summary = new Summary();
            int amountNew = 0;
            int amountResolved = 0;
            int amountApproved = 0;
            int amountDisapproved = 0;
            int amountAssigned = 0;
            int amountClosed = 0;
            Iterable<Ticket> tickets = ticketService.findAll();
            for(Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
                Ticket ticket = iterator.next();
                if(ticket.getStatus().equals(StatusEnum.New)) {
                    amountNew++;
                }
                if(ticket.getStatus().equals(StatusEnum.Resolved)) {
                    amountResolved++;
                }
                if(ticket.getStatus().equals(StatusEnum.Approved)) {
                    amountApproved++;
                }
                if(ticket.getStatus().equals(StatusEnum.Disapproved)) {
                    amountDisapproved++;
                }
                if(ticket.getStatus().equals(StatusEnum.Assigned)) {
                    amountAssigned++;
                }
                if(ticket.getStatus().equals(StatusEnum.Closed)) {
                    amountClosed++;
                }
            }
            summary.setAmountNew(amountNew);
            summary.setAmountResolved(amountResolved);
            summary.setAmountApproved(amountApproved);
            summary.setAmountDisapproved(amountDisapproved);
            summary.setAmountAssigned(amountAssigned);
            summary.setAmountClosed(amountClosed);
            summaryResponse.setData(summary);
        } catch (Exception e) {
            summaryResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(summaryResponse);
        }
        return ResponseEntity.ok(summaryResponse);
    }

    private void validateChangeStatus(String id, String status, BindingResult result) {
        if(id == null || id.isEmpty())
            result.addError(new ObjectError("Ticket", "Id not informed"));

        if(status == null || status.isEmpty())
            result.addError(new ObjectError("Ticket", "Status not informed"));
    }

    private void validateUpdateTicket(Ticket ticket, BindingResult result) {
        if(ticket.getId() == null)
            result.addError(new ObjectError("Ticket", "Id not informed"));

        if(ticket.getTitle() == null)
            result.addError(new ObjectError("Ticket", "Title not informed"));
    }

}