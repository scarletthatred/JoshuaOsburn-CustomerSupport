package  com.example.customersupport;



public class Ticket {
    private String customerName;
    private String subject;
    private String bodyTicket;
    public Attachment attachment;

    public Ticket() {
        super();
    }

    public Ticket(String customerName, String subject, String bodyTicket, Attachment attachments) {
        this.customerName = customerName;
        this.subject = subject;
        this.bodyTicket = bodyTicket;
        this.attachment = attachments;
    }

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

    public Attachment getAttachments() {
        return attachment;
    }

    public void setAttachments(Attachment attachments) {
        this.attachment = attachments;
    }

    public boolean hasAttachment() {
        return attachment.getName().length() > 0 || attachment.getContents().length>0;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "customerName='" + customerName + '\'' +
                ", subject='" + subject + '\'' +
                ", bodyTicket='" + bodyTicket + '\'' +
                ", attachments=" + attachment +
                '}';
    }

}