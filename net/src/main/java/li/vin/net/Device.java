package li.vin.net;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import auto.parcel.AutoParcel;
import rx.Observable;

@AutoParcel
public abstract class Device implements VinliItem {
  /*package*/ static final Type WRAPPED_TYPE = new TypeToken<Wrapped<Device>>() { }.getType();
  /*package*/ static final Type PAGE_TYPE = new TypeToken<Page<Device>>() { }.getType();

  /*package*/ static final void registerGson(GsonBuilder gb) {
    gb.registerTypeAdapter(Device.class, AutoParcelAdapter.create(AutoParcel_Device.class));
    gb.registerTypeAdapter(Links.class, AutoParcelAdapter.create(AutoParcel_Device_Links.class));
    gb.registerTypeAdapter(WRAPPED_TYPE, Wrapped.Adapter.create(Device.class));
    gb.registerTypeAdapter(PAGE_TYPE, Page.Adapter.create(PAGE_TYPE, Device.class));
  }

  /*package*/ abstract Links links();

  /*package*/ Device() { }

  public Observable<Page<Vehicle>> vehicles() {
    return vehicles(null, null);
  }

  public Observable<Page<Vehicle>> vehicles(
      @Nullable Integer limit,
      @Nullable Integer offset) {
    return Vinli.curApp().vehicles().vehicles(id(), limit, offset);
  }

  public Observable<Vehicle> vehicle(@NonNull String vehicleId) {
    return Vinli.curApp().vehicles().vehicle(id(), vehicleId)
        .map(Wrapped.<Vehicle>pluckItem());
  }

  public Observable<Vehicle> latestVehicle() {
    return vehicle("_latest");
  }

  public Observable<Page<Rule>> rules() {
    return rules(null, null);
  }

  public Observable<Page<Rule>> rules(
      @Nullable Integer limit,
      @Nullable Integer offset) {
    return Vinli.curApp().rules().rules(id(), limit, offset);
  }

  public Observable<Rule> rule(@NonNull String ruleId) {
    return Vinli.curApp().rules().rule(id(), ruleId)
        .map(Wrapped.<Rule>pluckItem());
  }

  public Observable<TimeSeries<Event>> events() {
    return events(null, null, null, null, null);
  }

  public Observable<TimeSeries<Event>> events(
      @Nullable String type,
      @Nullable String objectId,
      @Nullable Date since,
      @Nullable Date until,
      @Nullable Integer limit) {
    return Vinli.curApp().events().events(id(), type, objectId, since, until, limit);
  }

  public Observable<TimeSeries<Location>> locations() {
    return locations(null, null, null, null, null);
  }

  public Observable<TimeSeries<Location>> locations(
      @Nullable String fields,
      @Nullable Date until,
      @Nullable Date since,
      @Nullable Integer limit,
      @Nullable String sortDir) {
    return Vinli.curApp().locations().locations(id(), fields, until, since, limit, sortDir);
  }

  public Observable<Location> latestlocation() {
    return locations(null, null, null, 1, null)
        .flatMap(TimeSeries.<Location>extractItems())
        .firstOrDefault(null);
  }

  public Observable<TimeSeries<Snapshot>> snapshots() {
    return snapshots(null, null, null, null, null);
  }

  public Observable<TimeSeries<Snapshot>> snapshots(
      @Nullable String fields,
      @Nullable Date until,
      @Nullable Date since,
      @Nullable Integer limit,
      @Nullable String sortDir) {
    return Vinli.curApp().snapshots().snapshots(id(), fields, until, since, limit, sortDir);
  }

  public Observable<Page<Subscription>> subscriptions() {
    return subscriptions(null, null, null, null);
  }

  public Observable<Page<Subscription>> subscriptions(
      @Nullable Integer limit,
      @Nullable Integer offset,
      @Nullable String objectId,
      @Nullable String objectType) {
    return Vinli.curApp().subscriptions().subscriptions(id(), limit, offset, objectId, objectType);
  }

  public Observable<Subscription> subscription(@NonNull String subscriptionId) {
    return Vinli.curApp().subscriptions().subscription(id(), subscriptionId)
        .map(Wrapped.<Subscription>pluckItem());
  }

  @AutoParcel
  /*package*/ static abstract class Links implements Parcelable {
    public abstract String self();
    public abstract String rules();
    public abstract String vehicles();
    public abstract String latestVehicle();

    /*package*/ Links() { }
  }
}
