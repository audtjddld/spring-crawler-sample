package com.sample.common.company;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Company {

  @JsonProperty("companyId")
  private String pipeId;
  @JsonProperty("type")
  private Hosting type;
  @JsonProperty("seedURL")
  private String seedURL;

}
