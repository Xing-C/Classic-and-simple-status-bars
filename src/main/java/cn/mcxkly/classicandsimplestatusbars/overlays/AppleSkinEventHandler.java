package cn.mcxkly.classicandsimplestatusbars.overlays;

import net.neoforged.bus.api.SubscribeEvent;
import squeek.appleskin.api.event.HUDOverlayEvent;

public class AppleSkinEventHandler {
    // 取消苹果皮MOD的部分渲染，应该能增加性能.
    // 1.21 起 HUDOverlayEvent 直接实现 ICancellableEvent，无需再判断 isCancelable。
    @SubscribeEvent
    public void StopRenderingHunger(HUDOverlayEvent.HungerRestored event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void StopRenderingSaturation(HUDOverlayEvent.Saturation event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void StopRenderingEstimated(HUDOverlayEvent.Exhaustion event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void StopRenderingHealth(HUDOverlayEvent.HealthRestored event) {
        event.setCanceled(true);
    }
}
