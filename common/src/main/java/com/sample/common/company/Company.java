package com.sample.common.company;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Company {

  private CompanyId companyId;
  private Hosting type;

}
