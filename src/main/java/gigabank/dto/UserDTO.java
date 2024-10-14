package gigabank.dto;

import gigabank.validation.AgeConstraint;
import gigabank.validation.PhoneConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    @NotBlank(message = "Name must not be blank")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(message = "Phone must not be null")
    @PhoneConstraint
    private String phone;
    @NotNull(message = "BirthDate must not be null")
    @AgeConstraint
    private LocalDate birthDate;
    private List<Long> bankAccountsIds = new ArrayList<>();
}