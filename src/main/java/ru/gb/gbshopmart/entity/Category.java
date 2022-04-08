package ru.gb.gbshopmart.entity;

import lombok.*;
import ru.gb.gbshopmart.entity.common.InfoEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends InfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;


    public boolean addProduct(Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        return products.add(product);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", products=" + products +
                '}';
    }

}
