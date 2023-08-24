package data;

public class PeopleCreated  extends People{
    private Integer id;
    private String createdAt;

    public PeopleCreated(){
        super();
    }

    public PeopleCreated(Integer id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public PeopleCreated(String name, String job, Integer id, String createdAt) {
        super(name, job);
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void getCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return super.getName();
    }
    public String getJob() {
        return super.getJob();
    }
}
