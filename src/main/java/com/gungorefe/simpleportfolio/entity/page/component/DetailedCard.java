package com.gungorefe.simpleportfolio.entity.page.component;

import com.gungorefe.simpleportfolio.dto.page.component.CardDto;
import jakarta.persistence.Column;
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
public class DetailedCard extends Component {
    protected String imageName;
    protected String title;
    @Column(columnDefinition = "text")
    protected String text;
    protected int displayOrder;

    public DetailedCard(int id, String imageName) {
        super(id);
        this.imageName = imageName;
    }

    public DetailedCard(String imageName, String title, String text, int displayOrder) {
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.displayOrder = displayOrder;
    }

    public DetailedCard(int id, String imageName, String title, String text, int displayOrder) {
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

    public static List<CardDto> toDtoList(Collection<? extends DetailedCard> cards) {
        return Stream.ofNullable(cards)
                .flatMap(Collection::stream)
                .map(DetailedCard::toDto)
                .toList();
    }
}
