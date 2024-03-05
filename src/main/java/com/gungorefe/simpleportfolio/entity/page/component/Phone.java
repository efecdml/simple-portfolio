package com.gungorefe.simpleportfolio.entity.page.component;

import com.gungorefe.simpleportfolio.dto.page.component.PhoneDto;
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
public class Phone extends Component {
    protected String tag;
    protected String number;
    protected int displayOrder;

    public Phone(int id) {
        super(id);
    }

    public Phone(String tag, String number, int displayOrder) {
        this.tag = tag;
        this.number = number;
        this.displayOrder = displayOrder;
    }

    public PhoneDto toDto() {
        return new PhoneDto(
                id,
                tag,
                number,
                displayOrder
        );
    }

    public static List<PhoneDto> toDtoList(Collection<? extends Phone> phones) {
        return Stream.ofNullable(phones)
                .flatMap(Collection::stream)
                .map(Phone::toDto)
                .toList();
    }
}
