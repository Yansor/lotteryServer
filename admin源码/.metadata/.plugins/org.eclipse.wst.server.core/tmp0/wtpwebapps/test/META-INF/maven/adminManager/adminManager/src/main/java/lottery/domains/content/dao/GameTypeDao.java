package lottery.domains.content.dao;

import lottery.domains.content.entity.GameType;
import java.util.List;

public interface GameTypeDao
{
    List<GameType> listAll();
}
