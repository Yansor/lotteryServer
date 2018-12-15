package lottery.domains.content.api.pt;

public class PTPlayerGamePagination
{
  private int currentPage;
  private int totalPages;
  private int itemsPerPage;
  private int totalCount;
  
  public int getCurrentPage()
  {
    return this.currentPage;
  }
  
  public void setCurrentPage(int currentPage)
  {
    this.currentPage = currentPage;
  }
  
  public int getTotalPages()
  {
    return this.totalPages;
  }
  
  public void setTotalPages(int totalPages)
  {
    this.totalPages = totalPages;
  }
  
  public int getItemsPerPage()
  {
    return this.itemsPerPage;
  }
  
  public void setItemsPerPage(int itemsPerPage)
  {
    this.itemsPerPage = itemsPerPage;
  }
  
  public int getTotalCount()
  {
    return this.totalCount;
  }
  
  public void setTotalCount(int totalCount)
  {
    this.totalCount = totalCount;
  }
}
