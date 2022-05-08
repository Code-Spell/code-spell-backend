package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "levels")
@Generated
@Data
@AllArgsConstructor
public class Level implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    private String title;

    private String description;

    private Chapter chapter;

    private int number;

    @NonNull
    private List<Goal> goals;

    private List<Tip> tips;

    private List<Documentation> documentation;

    private List<Solution> solutions;

    @NonNull
    private Settings settings;

}
