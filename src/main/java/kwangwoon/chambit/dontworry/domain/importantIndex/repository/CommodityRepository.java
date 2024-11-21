package kwangwoon.chambit.dontworry.domain.importantIndex.repository;

import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Commodity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CommodityRepository {

    private final IndexInfoRepository indexInfoService;

    public List<Commodity> getCommodities() {
        return indexInfoService.getEntitiesByPattern("commodity:*",Commodity.class);
    }
}
