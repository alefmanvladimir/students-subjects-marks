package telran.students.dto;

import javax.validation.constraints.*;

public class QueryDto {
    @NotNull
    public QueryType type;
    @NotNull
    public String query;
}
