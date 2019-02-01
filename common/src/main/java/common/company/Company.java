package common.company;


public class Company {

  private CompanyId companyId;
  private Hosting type;

  public Company(CompanyId companyId, Hosting type) {
    this.companyId = companyId;
    this.type = type;
  }

  public CompanyId getCompanyId() {
    return companyId;
  }

  public Hosting getType() {
    return type;
  }
}
