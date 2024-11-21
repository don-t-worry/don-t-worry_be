package kwangwoon.chambit.dontworry.domain.importantIndex.repository;

import kwangwoon.chambit.dontworry.domain.importantIndex.domain.Index;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IndexRepository {
    private final IndexInfoRepository indexInfoService;

    public List<Index> getIndexes(){
        return indexInfoService.getEntitiesByPattern("index:*", Index.class);
    }
}
