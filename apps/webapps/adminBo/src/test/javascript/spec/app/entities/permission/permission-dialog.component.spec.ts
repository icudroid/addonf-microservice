/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { PermissionDialogComponent } from '../../../../../../main/webapp/app/entities/permission/permission-dialog.component';
import { PermissionService } from '../../../../../../main/webapp/app/entities/permission/permission.service';
import { Permission } from '../../../../../../main/webapp/app/entities/permission/permission.model';

describe('Component Tests', () => {

    describe('Permission Management Dialog Component', () => {
        let comp: PermissionDialogComponent;
        let fixture: ComponentFixture<PermissionDialogComponent>;
        let service: PermissionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [PermissionDialogComponent],
                providers: [
                    PermissionService
                ]
            })
            .overrideTemplate(PermissionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PermissionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Permission(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.permission = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'permissionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Permission();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.permission = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'permissionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
