package com.example.customersupport.site;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("ticket")
public class TicketController {
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @RequestMapping(value={"list",""})
    public String listBlogs(Model model){
        model.addAttribute("ticketDatabase", ticketDB);
        return "listTickets";
    }

    @GetMapping("create")
    public ModelAndView createTicket(){
        return new ModelAndView("ticketForm", "ticket", new TicketForm());
    }
    @PostMapping("create")
    public View createTicket(@ModelAttribute("ticket") TicketForm form) throws IOException {
      Ticket ticket = new Ticket();
      ticket.setCustomerName(form.getCustomerName());
      ticket.setSubject(form.getSubject());
      ticket.setBodyTicket(form.getBodyTicket());
      MultipartFile file = form.getAttachment();
      Attachment attachment = new Attachment();
      attachment.setName(file.getOriginalFilename());
      attachment.setContents(file.getBytes());
      if ((attachment.getName() != null && attachment.getName().length() >0) || (attachment.getContents() != null && attachment.getContents().length > 0)){
          ticket.setAttachment(attachment);
      }
        int id;
        synchronized (this){
            id = this.TICKET_ID++;
            ticketDB.put(id,ticket);
        }

        //showing the view with the ticket Id
        return new RedirectView("view/"+id,true, false);
    }

    @GetMapping("view/{ticketId}")
        public ModelAndView viewTicket(Model model, @PathVariable("ticketId")int ticketId){
            Ticket ticket = ticketDB.get(ticketId);

            if(ticket == null){
                return new ModelAndView(new RedirectView("ticket/list",true,false));
            }
            //found the ticket
            model.addAttribute("ticketId",ticketId);
            model.addAttribute("ticket",ticket);
            return new ModelAndView("viewTicket");

    }

    @GetMapping("/{ticketId}/attachment/{attachment:.+}")
    public View downloadAttachment(@PathVariable("ticketId")int ticketId, @PathVariable("attachment")String name){
        Ticket ticket = ticketDB.get(ticketId);
        // no ticket
        if (ticket == null){
            return new RedirectView("listTickets",true,false);
        }

        //checks for an attachment
        Attachment attachment = ticket.getAttachments();
        if (attachment == null){
            return new RedirectView("listTickets",true,false);
        }
        //otherwise there is an image
        return new DownloadView(attachment.getName(), attachment.getContents());
    }

    public static class TicketForm {
        private String customerName;
        private String subject;
        private String bodyTicket;
        private MultipartFile attachment;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBodyTicket() {
            return bodyTicket;
        }

        public void setBodyTicket(String bodyTicket) {
            this.bodyTicket = bodyTicket;
        }

        public MultipartFile getAttachment() {
            return attachment;
        }

        public void setAttachment(MultipartFile attachment) {
            this.attachment = attachment;
        }
    }

}
