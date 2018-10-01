package com.aequilibrium.assignment.transfarena.injector;

import com.aequilibrium.assignment.transfarena.battle.ui.BattleActivity;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryActivity;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GalleryActivity galleryActivity);

    void inject(PreviewActivity previewActivity);

    void inject(BattleActivity battleActivity);

}
