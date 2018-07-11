package bd;

import static bd.Utils.MAX_STRING_BYTE_LENGTH;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.time.LocalDateTime;

public class Ingredient implements Record { // Ингредиент (склад)

    public static final int SIZE = 40 + 2 * Utils.MAX_STRING_BYTE_LENGTH;
    private int id;
    private String name = "";
    private LocalDateTime productionDate;
    private double amount; // Объем
    private Duration shelfLife; // Срок годности
    private double price; // Стоимость
    private String provider = ""; // Поставщик

    public Ingredient() {
    }

    public Ingredient(int id) {
        this.id = id;
    }

    public Ingredient(int id, String name, LocalDateTime productionDate, double amount, Duration shelfLife, double price, String provider) throws Exception {
        this.id = id;
        setName(name);
        this.productionDate = productionDate;
        this.amount = amount;
        this.shelfLife = shelfLife;
        this.price = price;
        setProvider(provider);
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) throws Exception {
        if (name == null) {
            name = "";
        }
        if (name.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Строка превышает допустимую длину!");
        }
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public Duration getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Duration shelfLife) {
        this.shelfLife = shelfLife;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public final void setProvider(String provider) throws Exception {
        if (provider == null) {
            provider = "";
        }
        if (provider.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Название поставщика превышает допустимую длину!");
        }
        this.provider = provider;
    }

    @Override
    public void write(RandomAccessFile f) throws IOException, Exception {
        f.writeInt(id);
        Utils.writeString(f, name);
        Utils.writeLocalDateTime(f, productionDate);
        f.writeDouble(amount);
        Utils.writeDuration(f, shelfLife);
        f.writeDouble(price);
        Utils.writeString(f, provider);
    }

    @Override
    public void read(RandomAccessFile f) throws IOException {
        id = f.readInt();
        name = Utils.readString(f);
        productionDate = Utils.readLocalDateTime(f);
        amount = f.readDouble();
        shelfLife = Utils.readDuration(f);
        price = f.readDouble();
        provider = Utils.readString(f);
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Record cloneWithoutId(int id) throws Exception {
        return new Ingredient(id, name, productionDate, amount, shelfLife, price, provider);
    }
}
