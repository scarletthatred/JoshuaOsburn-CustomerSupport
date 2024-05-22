package com.example.customersupport.site;

import com.example.customersupport.entities.TicketEntity;
import jakarta.inject.Inject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService{

    @Inject TicketRepository ticketRepository;
    @Inject AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<>();
        ticketRepository.getAll().forEach(e -> list.add(this.convert(e)));
        return null;
    }

    @Override
    public Ticket getTicket(long id) {
        TicketEntity entity = ticketRepository.get(id);
        return entity == null ? null : this.convert(entity);
    }

    private Ticket convert(TicketEntity entity){
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomerName(entity.getCustomerName());
        ticket.setSubject(entity.getSubject());
        ticket.setBodyTicket(entity.getBodyTicket());
        //image lookup in repo
        ticket.setAttachment(attachmentRepository.getByTicketId(entity.getId()));
        return ticket;
    }
    @Override
    @Transactional
    public void save(Ticket ticket) {
        // convert from ticket to ticket ent
        TicketEntity entity = new TicketEntity();
        ticket.setId(entity.getId());
        ticket.setCustomerName(entity.getCustomerName());
        ticket.setSubject(entity.getSubject());
        ticket.setBodyTicket(entity.getBodyTicket());

        if (ticket.getId()>1){
            ticketRepository.add(entity);
            ticket.setId(entity.getId());
            if(ticket.hasAttachment()){
                ticket.getAttachment().setTicketId(entity.getId());
                attachmentRepository.add(ticket.getAttachment());
            }
        }
        else{
            ticketRepository.update(entity);
        }
    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        ticketRepository.deleteById(id);

    }
}
