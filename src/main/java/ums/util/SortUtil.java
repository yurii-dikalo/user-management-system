package ums.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final int ORDER_DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static final String SPLIT_DELIMITER = ":";

    public Sort getSort(String sortBy) {
        Sort.Order order;
        if (sortBy.contains(":")) {
            String[] fieldAndOrder = sortBy.split(SPLIT_DELIMITER);
            order = new Sort.Order(
                    Sort.Direction.valueOf(fieldAndOrder[ORDER_DIRECTION_INDEX]),
                    fieldAndOrder[FIELD_INDEX]);
        } else {
            order = Sort.Order.asc(sortBy);
        }
        return Sort.by(order);
    }
}
