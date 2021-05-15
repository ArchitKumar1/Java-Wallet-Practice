import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transaction {
    public String sendersName;
    public String receiversName;
    public int amount;
}

