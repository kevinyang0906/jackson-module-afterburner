package com.fasterxml.jackson.module.afterburner.deser;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerTestBase;

public class TestIssue14 extends AfterburnerTestBase
{
    public void testIssue() throws Exception
    {
        // create this ridiculously complicated object
        ItemData data = new ItemData();
        data.setDenomination(100);
        
        Item item = new Item();
        item.setData(data);
        item.setProductId(123);
        
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
    
        PlaceOrderRequest order = new PlaceOrderRequest();
        order.setOrderId(68723496);
        order.setUserId("123489043");
        order.setAmount(250);
        order.setStatus("placed");
        order.setItems(itemList);
        
        final Date now = new Date(999999L);
        
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        
        // create the mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new AfterburnerModule());
        
        // write the object to a string
        String json = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(order);

//System.out.println("JSON: "+json);
        
        // read the string and turn it back into an object
        // this will cause an exception unless the AfterburnerModule is commented out
        order = mapper.readValue(json, PlaceOrderRequest.class);
        assertNotNull(order);
        assertEquals(250, order.getAmount());
    }
}

class PlaceOrderRequest
{

     @JsonProperty("id")
     private long orderId;
     
     @JsonProperty("from")
     private String userId;
     
     private int amount;
     
     private String status;
     
     private List<Item> items;
     
     @JsonProperty("created_at")
     private Date createdAt;
     
     @JsonProperty("updated_at")
     private Date updatedAt;
     
     public long getOrderId() {
          return orderId;
     }

     public void setOrderId(long orderId) {
          this.orderId = orderId;
     }

     public String getUserId() {
          return userId;
     }

     public void setUserId(String userId) {
          this.userId = userId;
     }

     public int getAmount() {
          return amount;
     }

     public void setAmount(int amount) {
          this.amount = amount;
     }

     public String getStatus() {
          return status;
     }

     public void setStatus(String status) {
          this.status = status;
     }

     public List<Item> getItems() {
          return items;
     }

     public void setItems(List<Item> items) {
          this.items = items;
     }

     public Date getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(Date createdAt) {
          this.createdAt = createdAt;
     }

     public Date getUpdatedAt() {
          return updatedAt;
     }

     public void setUpdatedAt(Date updatedAt) {
          this.updatedAt = updatedAt;
     }
}
     
class Item {
      @JsonProperty("product_id")
      private int productId;
      
      private int quantity;
      
      private ItemData data;

      public int getProductId() {
           return productId;
      }

      public void setProductId(int productId) {
           this.productId = productId;
      }

      public int getQuantity() {
           return quantity;
      }

      public void setQuantity(int quantity) {
           this.quantity = quantity;
      }

      public ItemData getData() {
           return data;
      }

      public void setData(ItemData data) {
           this.data = data;
      }
 }
 
 @JsonInclude(Include.NON_NULL)
 class ItemData {
      private int denomination;
      private List<VLTBet> bets;
      
      public int getDenomination() {
           return denomination;
      }
      
      public void setDenomination(int denomination) {
           this.denomination = denomination;
      }
      
      public List<VLTBet> getBets() {
           return bets;
      }
      
      public void setBets(List<VLTBet> bets) {
           this.bets = bets;
      }
 }
 
 @JsonInclude(Include.NON_NULL)
 class VLTBet {
      private int index;
      private String selection;
      private int stake;
      private int won;
      public int getIndex() {
           return index;
      }
      public void setIndex(int index) {
           this.index = index;
      }
      public String getSelection() {
           return selection;
      }
      public void setSelection(String selection) {
           this.selection = selection;
      }
      public int getStake() {
           return stake;
      }
      public void setStake(int stake) {
           this.stake = stake;
      }
      public int getWon() {
           return won;
      }
      public void setWon(int won) {
           this.won = won;
      }
}