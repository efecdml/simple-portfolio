package com.gungorefe.simpleportfolio.entity.page.component;

import com.gungorefe.simpleportfolio.dto.page.component.CarouselSectionDto;
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
public class CarouselSection extends Component {
    protected String imageName;
    protected String text;
    protected int displayOrder;

    public CarouselSection(int id, String imageName) {
        super(id);
        this.imageName = imageName;
    }

    public CarouselSection(String imageName, String text, int displayOrder) {
        this.imageName = imageName;
        this.text = text;
        this.displayOrder = displayOrder;
    }

    public CarouselSection(int id, String imageName, String text, int displayOrder) {
        super(id);
        this.imageName = imageName;
        this.text = text;
        this.displayOrder = displayOrder;
    }

    public CarouselSectionDto toDto() {
        return new CarouselSectionDto(
                id,
                imageName,
                text,
                displayOrder
        );
    }

    public static List<CarouselSectionDto> toDtoList(Collection<? extends CarouselSection> sections) {
        return Stream.ofNullable(sections)
                .flatMap(Collection::stream)
                .map(CarouselSection::toDto)
                .toList();
    }
}
