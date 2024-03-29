package com.aequilibrium.assignment.transfarena.injector;

import com.aequilibrium.assignment.transfarena.battle.ui.activity.BattleActivity;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryActivity;
import com.aequilibrium.assignment.transfarena.gallery.ui.fragment.GalleryPageFragment;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GalleryActivity galleryActivity);

    void inject(PreviewActivity previewActivity);

    void inject(BattleActivity battleActivity);

    void inject(GalleryPageFragment galleryPageFragment);

}
