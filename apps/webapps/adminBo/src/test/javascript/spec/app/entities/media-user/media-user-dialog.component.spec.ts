/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { MediaUserDialogComponent } from '../../../../../../main/webapp/app/entities/media-user/media-user-dialog.component';
import { MediaUserService } from '../../../../../../main/webapp/app/entities/media-user/media-user.service';
import { MediaUser } from '../../../../../../main/webapp/app/entities/media-user/media-user.model';
import { MediaService } from '../../../../../../main/webapp/app/entities/media';

describe('Component Tests', () => {

    describe('MediaUser Management Dialog Component', () => {
        let comp: MediaUserDialogComponent;
        let fixture: ComponentFixture<MediaUserDialogComponent>;
        let service: MediaUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [MediaUserDialogComponent],
                providers: [
                    MediaService,
                    MediaUserService
                ]
            })
            .overrideTemplate(MediaUserDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaUserDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MediaUser(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mediaUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mediaUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MediaUser();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mediaUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mediaUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
