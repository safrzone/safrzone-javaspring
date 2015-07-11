package hello.incident;

public interface LocationDao {
	public void save(Location location);
	public Location findByLocationId(long locationId);
}
