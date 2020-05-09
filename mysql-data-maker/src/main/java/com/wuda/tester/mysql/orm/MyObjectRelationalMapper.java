package com.wuda.tester.mysql.orm;

import com.apifan.common.random.source.InternetSource;
import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.OtherSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.entity.IndividualUser;
import com.wuda.tester.mysql.entity.Item;
import com.wuda.tester.mysql.entity.Shop;
import com.wuda.tester.mysql.entity.Warehouse;
import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.code.generator.lang.TableEntityUtils;
import com.wuda.yhan.util.commons.RandomUtilsExt;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class MyObjectRelationalMapper implements ObjectRelationalMapper {
    /**
     * 命令行参数.
     */
    private CliArgs cliArgs;

    /**
     * 生成ORM实例.
     *
     * @param cliArgs 命令行参数
     */
    public MyObjectRelationalMapper(CliArgs cliArgs) {
        this.cliArgs = cliArgs;
    }

    @Override
    public List<TableEntity> insertTransaction() {
        // 用户
        IndividualUser individualUser = TableEntityUtils.genRandomValueInstance(IndividualUser.class);
        long userId = individualUser.getId();
        // 重新设置值,因为随机生成的值太像乱码了
        individualUser.setUsername(PersonInfoSource.getInstance().randomChineseName());
        individualUser.setEmail(InternetSource.getInstance().randomEmail(10));
        individualUser.setPassword(PersonInfoSource.getInstance().randomStrongPassword(16, true));
        individualUser.setMobilePhone(Long.parseLong(PersonInfoSource.getInstance().randomChineseMobile()));
        individualUser.setStatus(NumberSource.getInstance().randomInt(10, 21));

        individualUser.setIsDeleted((long)NumberSource.getInstance().randomInt(1,2));
        // 店铺
        Shop shop = TableEntityUtils.genRandomValueInstance(Shop.class);
        shop.setShopName(OtherSource.getInstance().randomCompanyName("北京"));
        shop.setIsDeleted((long)NumberSource.getInstance().randomInt(1,2));

        // 建立店铺与用户关系
        shop.setUserId(userId);
        // 仓库
        Warehouse warehouse = TableEntityUtils.genRandomValueInstance(Warehouse.class);
        warehouse.setWarehouseType(NumberSource.getInstance().randomInt(1,7));
        warehouse.setWarehouseName(PersonInfoSource.getInstance().randomChineseNickName(5));
        warehouse.setIsDeleted((long)NumberSource.getInstance().randomInt(1,2));
        // 建立仓库与用户和店铺的关系
        warehouse.setUserId(userId);
        warehouse.setShopId(shop.getId());
        // 每个用户(店铺)生成随机的商品数
        int itemCount = RandomUtils.nextInt(1, cliArgs.getMaxItemPerUser() + 1);
        List<TableEntity> list = new ArrayList<>(3 + itemCount);
        list.add(individualUser);
        list.add(shop);
        list.add(warehouse);
        genItem(individualUser.getId(), shop.getId(), itemCount, list);
        return list;
    }

    /**
     * 为用户生成给定的商品数.
     *
     * @param userId    user id
     * @param shopId    shop id
     * @param count     商品数
     * @param container container
     */
    private void genItem(Long userId, Long shopId, int count, List<TableEntity> container) {
        for (int i = 0; i < count; i++) {
            Item item = TableEntityUtils.genRandomValueInstance(Item.class);
            item.setShopId(shopId);
            item.setCategoryOneId((long)NumberSource.getInstance().randomInt(101,110));
            item.setCategoryTwoId((long)NumberSource.getInstance().randomInt(1001,1010));
            item.setCategoryThreeId((long)NumberSource.getInstance().randomInt(1001,1010));
            item.setItemName(OtherSource.getInstance().randomChinese(8));
            item.setIsDeleted((long)NumberSource.getInstance().randomInt(1,2));
            item.setItemType(NumberSource.getInstance().randomInt(201,209));
            container.add(item);
        }
    }

}
