package org.agoncal.sample.arquilian.wytiwyr;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Book.FIND_ALL_SCIFI, query = "SELECT b FROM Book b WHERE 'scifi' member of b.tags ORDER BY b.id DESC")
})
@XmlRootElement
public class Book {

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_ALL_SCIFI = "Book.findAllScifiBooks";

    // ======================================
    // =             Attributes             =
    // ======================================
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 4, max = 50, message = "Title should be between {min} and {max}")
    private String title;
    private Float price;
    @Column(length = 2000)
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
    private String language;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tags")
    private List<String> tags = new ArrayList<String>();

    // ======================================
    // =            Constructors            =
    // ======================================

    public Book() {
    }

    public Book(String title, Float price, String description, Integer nbOfPage, Boolean illustrations, String language, String tag, String isbn) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.nbOfPage = nbOfPage;
        this.illustrations = illustrations;
        this.language = language;
        this.isbn = isbn;
        this.addTag(tag);
    }

    public Book(String title, Float price, String description, Integer nbOfPage, Boolean illustrations, String language, String tag) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.nbOfPage = nbOfPage;
        this.illustrations = illustrations;
        this.language = language;
        this.addTag(tag);
    }

    public Book(String title, Float price, String description, Integer nbOfPage, Boolean illustrations, String language) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.nbOfPage = nbOfPage;
        this.illustrations = illustrations;
        this.language = language;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public Boolean getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Boolean illustrations) {
        this.illustrations = illustrations;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // ======================================
    // =         hash, equals, toString     =
    // ======================================

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Book");
        sb.append("{id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append(", nbOfPage=").append(nbOfPage);
        sb.append(", illustrations=").append(illustrations);
        sb.append(", language='").append(language).append('\'');
        sb.append(", tags=").append(tags);
        sb.append('}');
        return sb.toString();
    }
}
