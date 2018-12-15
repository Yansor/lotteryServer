package lottery.domains.content.vo.config;

public class PortalCDNConfig
{
  private String cdnDomain;
  private String cdnVersion;
  
  public String getCdnDomain()
  {
    return this.cdnDomain;
  }
  
  public void setCdnDomain(String cdnDomain)
  {
    this.cdnDomain = cdnDomain;
  }
  
  public String getCdnVersion()
  {
    return this.cdnVersion;
  }
  
  public void setCdnVersion(String cdnVersion)
  {
    this.cdnVersion = cdnVersion;
  }
}
