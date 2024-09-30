package kwangwoon.chambit.dontworry.global.common.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class PageResponseDto<T> {
    List<T> content;
    PageInfo pageInfo;

    public PageResponseDto(Page<T> page){
        this.content = page.getContent();

        this.pageInfo = new PageInfo(page.getPageable().getPageNumber()
        ,page.getSize(), page.hasNext(), page.getTotalElements(), page.getTotalPages());
    }

    @Data
    public static class PageInfo{
        Integer page;
        Integer size;
        Boolean hasNextPage;
        Long totalContents;
        Integer totalPages;

        public PageInfo(int page, int size, boolean hasNextPage, long totalContents, int totalPages){
            this.page = page;
            this.size = size;
            this.hasNextPage = hasNextPage;
            this.totalContents = totalContents;
            this.totalPages = totalPages;
        }
    }
}
