package com.ted.auction_app.Models.Bid;

import com.ted.auction_app.Models.User.BidderXml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlType(propOrder = {
        "bidder",
        "time",
        "amount"
})
public class BidXml {

    private BidderXml bidder;
    private Date time;
    private Double amount;

    public BidXml() {
    }

    public BidXml(Date time, Double amount) {
        this.time = time;
        this.amount = amount;
        this.bidder = new BidderXml();
    }

    @XmlElement(name = "Bidder")
    public BidderXml getBidder() {
        return bidder;
    }

    public void setBidder(BidderXml bidder) {
        this.bidder = bidder;
    }

    @XmlElement(name = "Time")
    public String getTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(time);
        return strDate;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlElement(name = "Amount")
    public String getAmount() {
        return "$"+amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
