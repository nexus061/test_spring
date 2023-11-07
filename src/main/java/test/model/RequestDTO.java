package test.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

/**
 * Instantiates a new template documento DTO.
 */

/**
 * Instantiates a new template documento DTO.
 */
@NoArgsConstructor
public class RequestDTO {
	
	private String code;
	
	@NotNull
	private String name;

    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate endDate;
 
    
    /**
	 * Checks if is range valido.
	 *
	 * @return true, if is range valido
	 */
    @JsonIgnore
    @AssertTrue(message = "Range not valid")
    public boolean isRangeValid() 
    {
    	if(getEndDate()==null) return true;
    	
    	if(getStartDate()==null) return true;

        if (getStartDate().isEqual(getEndDate())) return true;

    	return getEndDate().isAfter(getStartDate());
    }

}
