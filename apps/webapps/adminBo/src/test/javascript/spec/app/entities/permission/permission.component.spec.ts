/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { PermissionComponent } from '../../../../../../main/webapp/app/entities/permission/permission.component';
import { PermissionService } from '../../../../../../main/webapp/app/entities/permission/permission.service';
import { Permission } from '../../../../../../main/webapp/app/entities/permission/permission.model';

describe('Component Tests', () => {

    describe('Permission Management Component', () => {
        let comp: PermissionComponent;
        let fixture: ComponentFixture<PermissionComponent>;
        let service: PermissionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [PermissionComponent],
                providers: [
                    PermissionService
                ]
            })
            .overrideTemplate(PermissionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PermissionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Permission(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.permissions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
