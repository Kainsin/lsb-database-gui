/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This reduced {@link org.kainsin.lsbdatabasegui.entities.items.ItemEntity} only contains the information we need for
 * the selection list. This simplifies what data we are getting from the database.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_basic")
@NamedQueries(value = {@NamedQuery(name = "ItemEntityReduced.findAll", query = "SELECT e FROM ItemEntityReduced e")})
public class ItemEntityReduced {
    @Id
    @Column(name = "itemId")
    @EqualsAndHashCode.Include
    @NotNull @Min(0)
    @Max(65535)
    private Integer id;

    @NotNull @Size(max = 255)
    private String name;
}
