/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import jakarta.persistence.Cacheable;
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
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_basic")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries(value = {@NamedQuery(name = "ItemEntity.findAll", query = "SELECT e FROM ItemEntity e")})
public class ItemEntity {
    @Id
    @Column(name = "itemId")
    @EqualsAndHashCode.Include
    @NotNull @Min(0)
    @Max(65535)
    private Integer id;

    @Column(name = "subid")
    @Min(0)
    @Max(65535)
    private int subId;

    @NotNull @Size(max = 255)
    private String name;

    @Column(name = "sortname")
    @NotNull @Size(max = 255)
    private String sortName;

    @Min(0)
    @Max(255)
    private int stackSize;

    @Column(name = "flags")
    private Set<ItemProperty> properties;

    @Column(name = "aH")
    private AuctionHouseCategory auctionHouseCategory;

    @Column(name = "NoSale")
    private boolean sellable;

    @Column(name = "BaseSell")
    private long baseSellPrice;
}
