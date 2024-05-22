package com.example.customersupport.site;

import com.example.customersupport.entities.Attachment;
public interface AttachmentRepository extends GenericRepository<Long, Attachment> {
    Attachment getByTicketId(Long ticketId);
}
