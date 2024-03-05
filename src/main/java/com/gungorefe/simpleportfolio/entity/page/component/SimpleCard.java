package com.gungorefe.simpleportfolio.entity.page.component;

import com.gungorefe.simpleportfolio.dto.page.component.CardDto;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@Setter
@MappedSuperclass
public class SimpleCard extends Component {
    protected String imageName;
    protected String title;
    protected String text;
    protected int displayOrder;

    public SimpleCard(int id, String imageName) {
        super(id);
        this.imageName = imageName;
    }

    public SimpleCard(String imageName, String title, String text, int displayOrder) {
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.displayOrder = displayOrder;
    }

    public SimpleCard(int id, String imageName, String title, String text, int displayOrder) {
        super(id);
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.displayOrder = displayOrder;
    }

    public CardDto toDto() {
        return new CardDto(
                id,
                imageName,
                title,
                text,
                displayOrder
        );
    }

    public static List<CardDto> toDtoList(Collection<? extends SimpleCard> cards) {
        return Stream.ofNullable(cards)
                .flatMap(Collection::stream)
                .map(SimpleCard::toDto)
                .toList();
    }
}
