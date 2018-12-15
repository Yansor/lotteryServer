package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.GameType;

public abstract interface GameTypeDao
{
  public abstract List<GameType> listAll();
}
